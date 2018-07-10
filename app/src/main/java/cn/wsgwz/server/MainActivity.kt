package cn.wsgwz.server

import android.app.Activity
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import cn.wsgwz.baselibrary.permission.Permission
import cn.wsgwz.baselibrary.permission.RequestPermissonListener
import cn.wsgwz.server.room.AppDatabase
import cn.wsgwz.server.room.RoomSqliteDataRetriever
import cn.wsgwz.server.room.User
import cn.wsgwz.server.service.FTPService
import cn.wsgwz.server.service.WebService
import com.ashokvarma.sqlitemanager.SqliteDataRetriever
import com.ashokvarma.sqlitemanager.SqliteManager
import com.eatbeancar.user.myapplication.LLog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppBaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var roomSqliteDataRetriever: SqliteDataRetriever
    lateinit var appDatabase: AppDatabase

    companion object {
        const val TAG = "MainActivity"
    }

    var iMyAidlInterface: IMyAidlInterface? = null
    private var serviceConnection: ServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        val intent = Intent(this@MainActivity, WebService::class.java)
        val intent1 = Intent(this@MainActivity, FTPService::class.java)
        intent1.putExtra(FTPService.EXTRA_KEY_STATE, true)
        doWithPermission(object : RequestPermissonListener {
            override val activity: Activity?
                get() = this@MainActivity
            override val permission: Permission
                get() = Permission.STORAGE
            override val requestCode: Int
                get() = 1002
            override val whyRequestPermission: String
                get() = getString(R.string.run_FTP_server)

            override fun success() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent1)
                } else {
                    startService(intent1)
                }
            }

        })


        serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
            }

            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(p1)
                iMyAidlInterface?.apply {
                    mainSwitch.isChecked = isStart
                }
            }
        }

        if (iMyAidlInterface == null) {
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }







        iMyAidlInterface?.apply {
            mainSwitch.isChecked = isStart
        }
        mainSwitch.setOnCheckedChangeListener { compoundButton, b ->
            intent.putExtra(WebService.EXTRA_KEY_STATE, b)
            if (b) {
                doWithPermission(object : RequestPermissonListener {
                    override val activity: Activity?
                        get() = this@MainActivity
                    override val permission: Permission
                        get() = Permission.STORAGE
                    override val requestCode: Int
                        get() = 1001
                    override val whyRequestPermission: String
                        get() = getString(R.string.run_web_server)

                    override fun success() {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(intent)
                        } else {
                            startService(intent)
                        }
                    }

                    override fun refuse() {
                        super.refuse()
                        mainSwitch.isChecked = false
                    }

                })
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent)
                } else {
                    startService(intent)
                }
            }


        }

        //LLog.d(TAG,"-->"+UserManager.getInstance().addUser(User("123456","张伟"," 追求真理的一个人")))

        appDatabase = AppDatabase.getDatabase(this)
        roomSqliteDataRetriever = RoomSqliteDataRetriever(appDatabase.openHelper)

     //   val cursor =  appDatabase.openHelper.writableDatabase.execSQL("select * from user  where id = 1 ")


        PreferenceManager.getDefaultSharedPreferences(this).apply {
            if (getBoolean("firstUse",true)){
                Thread(Runnable {
                    appDatabase.userModel().insertUser(User().apply {
                        id = 1
                        name = "张三"
                        password = "123456"
                        token = "登录后会根据token有效时间及tokenKey生成token"
                        tag = "认真对待每一件事"
                    })
                }).start()
                edit().putBoolean("firstUse",false).apply()
            }
        }



    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        iMyAidlInterface?.apply {
            unbindService(serviceConnection)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.data_base -> {
                doWithPermission(object : RequestPermissonListener {
                    override val activity: Activity?
                        get() = this@MainActivity
                    override val permission: Permission
                        get() = Permission.STORAGE
                    override val requestCode: Int
                        get() = 1001
                    override val whyRequestPermission: String
                        get() = getString(R.string.manager_data_base)

                    override fun success() {
                        SqliteManager.launchSqliteManager(this@MainActivity, roomSqliteDataRetriever, BuildConfig.APPLICATION_ID)
                    }

                    override fun refuse() {
                        super.refuse()
                    }

                })

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
