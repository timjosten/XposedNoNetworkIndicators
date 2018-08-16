package ru.timjosten.nonetworkindicators;

import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.*;

public class NoNetworkIndicators implements IXposedHookLoadPackage
{
  private static final String TAG = NoNetworkIndicators.class.getSimpleName() + ": ";

  public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam)
  throws Throwable
  {
    if(!lpparam.packageName.equals("com.android.systemui"))
      return;

    try
    {
      XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.SignalClusterView", lpparam.classLoader, "apply",
      new XC_MethodHook()
      {
        @Override
        protected void beforeHookedMethod(MethodHookParam param)
        throws Throwable
        {
          try
          {
            XposedHelpers.setBooleanField(param.thisObject, "mWifiIn", false);
            XposedHelpers.setBooleanField(param.thisObject, "mWifiOut", false);
          }
          catch(Throwable t)
          {
            XposedBridge.log(TAG + t);
          }
        }
      });
      XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.SignalClusterView.PhoneState", lpparam.classLoader, "apply", boolean.class,
      new XC_MethodHook()
      {
        @Override
        protected void beforeHookedMethod(MethodHookParam param)
        throws Throwable
        {
          try
          {
            XposedHelpers.setBooleanField(param.thisObject, "mActivityIn", false);
            XposedHelpers.setBooleanField(param.thisObject, "mActivityOut", false);
          }
          catch(Throwable t)
          {
            XposedBridge.log(TAG + t);
          }
        }
      });
    }
    catch(Throwable t)
    {
      XposedBridge.log(TAG + t);
    }
  }
}
