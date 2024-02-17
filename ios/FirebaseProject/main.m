#import <UIKit/UIKit.h>

#import "AppDelegate.h"
#import "SensorHAR-Swift.h"

int main(int argc, char *argv[])
{
  @autoreleasepool {
   // [[ReactUIUpdate sharedInstance]start];

    
//    SwiftClass* mySwiftClass = [[SwiftClass alloc]init];
//    [mySwiftClass sayHello];
    
//    Hello* h = [[Hello alloc]init];
//    [h hello];
    UIService* service = [[UIService alloc]init];
    //LocationService* locationService = [[LocationService alloc]init];
    return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
  }
}
