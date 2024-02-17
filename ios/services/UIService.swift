//
//  UIService.swift
//  DogFoodProject
//
//  Created by Zeki on 15.11.22.
//

import Foundation
import UIKit
@objc(UIService)
class UIService: RCTEventEmitter {
  @objc static var sharedInstance:UIService!
  // = UIService.init()
  var count:Int = 0;
  var timerPredict: Timer!
  
  var accSensor = AccelerationSensor(service:SensorService.sharedInstance, id:1)
  var gyroSensor = GyroscopeSensor(service:SensorService.sharedInstance, id:2)
  var magSensor = MagnetometerSensor(service:SensorService.sharedInstance, id:3)
  var orSensor = OrientationSensor(service:SensorService.sharedInstance, id:4)
  //var locationSensor = LocationSensor(service:SensorService.sharedInstance, id:5)
  
  var tf:InterpreterStorage? = InterpreterStorage()
  var send = false;
  var s = ""
  override init() {
    super.init()
    UIService.sharedInstance = self
    InterpreterStorage.sharedInstance.loadModel()
    accSensor.start()
    gyroSensor.start()
    magSensor.start()
    orSensor.start()
    let timer = Timer.scheduledTimer(timeInterval: 2, target: self, selector: #selector(predict), userInfo: nil, repeats: true)
    
    
  }

  @objc func predict() {
    
    if s=="" {
      s = InterpreterStorage.sharedInstance.label()
    }
    else{
      s = ""
    }
    
    if send {
      //print("status:", jsonString)
      sendEvent(withName: "onStatus", body: ["status": String(s)])
    }
  
  }
  
  override func supportedEvents() -> [String]! {
    return ["onSensor","onLocation","onTrip", "onStatus"]
  }
  override func constantsToExport() -> [AnyHashable : Any]! {
    return ["initialCount": count]
  }
  
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }
  
  @objc func passValueFromReact(_ value : String) {
    send = true;
      print(" Print Here \(value)")
   }
}
