package innovation.competition.com.knowsharing.application;

import android.app.Application;

import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;
import innovation.competition.com.knowsharing.base.UniversalImageLoader;
import innovation.competition.com.knowsharing.utils.DemoMessageHandler;
import innovation.competition.com.knowsharing.utils.StaticClass;

/**
 * 项目名：KnowSharing
 * 包名：application
 * 文件名：BaseApplication
 * 创建者：
 * 创建时间：2018/3/14 16:12
 * 描述：TODO
 */

public class BaseApplication extends Application {


    private static BaseApplication INSTANCE;

    public static BaseApplication INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(BaseApplication app) {
        setBmobIMApplication(app);
    }

    private static void setBmobIMApplication(BaseApplication a) {
        BaseApplication.INSTANCE = a;
    }

        @Override
    public void onCreate() {
        super.onCreate();


        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, false);
        //context = getApplicationContext();


        Bmob.initialize(this,StaticClass.BMOB_APP_ID);

            setInstance(this);
            //TODO 集成：1.8、初始化IM SDK，并注册消息接收器，只有主进程运行的时候才需要初始化
            if (getApplicationInfo().packageName.equals(getMyProcessName())) {
                BmobIM.init(this);
                BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));
            }
            Logger.init("BmobNewIMDemo");
            UniversalImageLoader.initImageLoader(this);


    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}
