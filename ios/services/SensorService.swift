//
//  SensorService.swift
//  DogFoodProject
//
//  Created by Zeki on 14.11.22.
//
import Foundation
import CoreMotion
import CoreLocation
class SensorService:NSObject {
  static var sharedInstance = SensorService()
 
  var sensors = [Sensor]()
  func register(sensor: Sensor){
    sensors.append(sensor)
  }
  func deregister(sensor: Sensor) {
          if let toRemove = sensors.enumerated().first(where: { $0.element.id == sensor.id })
          {
            sensors.remove(at: toRemove.offset)
          }
  }
  func receiveUpdate(sensorSample: SensorSample, type:SensorType) {
   
    //print("sample: ", sensorsSample)
    switch type {
    case .accelerometer:
      InterpreterStorage.sharedInstance.a.append(sensorSample)
      //InterpreterStorage.sharedInstance.predict()
     
      

    case .gyroscope:
      InterpreterStorage.sharedInstance.g.append(sensorSample)
      if (InterpreterStorage.sharedInstance.g.count>InterpreterStorage.sharedInstance.N_SAMPLES){
        InterpreterStorage.sharedInstance.g.removeFirst()
      }
     

    case .magnetometer:
      InterpreterStorage.sharedInstance.m.append(sensorSample)
      if (InterpreterStorage.sharedInstance.m.count>InterpreterStorage.sharedInstance.N_SAMPLES){
        InterpreterStorage.sharedInstance.m.removeFirst()
      }
      

    case .orientation:
      InterpreterStorage.sharedInstance.o.append(sensorSample)
      if (InterpreterStorage.sharedInstance.o.count>InterpreterStorage.sharedInstance.N_SAMPLES){
        InterpreterStorage.sharedInstance.o.removeFirst()
      }
    
      
      
    case .location:
      if (InterpreterStorage.sharedInstance.l.count>InterpreterStorage.sharedInstance.N_SAMPLES){
        InterpreterStorage.sharedInstance.l.removeFirst()
      }
      

    }
    
    }

}
