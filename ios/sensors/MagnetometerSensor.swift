//
//  GyroscopeSensor.swift
//  DogFoodProject
//
//  Created by Zeki on 14.11.22.
//
import Foundation
class MagnetometerSensor:Sensor{
  var startTime:Double = 0;
  var count:Int = 0;
  let id: Int
  var sensorSample = SensorSample()
  private var service : SensorService
  init(service : SensorService, id: Int) {
          self.id = id
          self.service = service
          self.service.register(sensor: self)

  }
    
    func start(){
      DispatchQueue.main.async {
        if MotionManager.shared.manager!.isMagnetometerAvailable {
          MotionManager.shared.manager!.magnetometerUpdateInterval = 0.001
          MotionManager.shared.manager!.startMagnetometerUpdates(to: .main) { [weak self](data, error) in
              guard let data = data, error == nil else {
                  return
              }
  //            if (self?.sample == nil)
  //            {
               // self?.sensorSample = self!.newSensorSample();
              //}
            
              self?.sensorSample.x = Float(data.magneticField.x)
              self?.sensorSample.y = Float(data.magneticField.y)
              self?.sensorSample.z = Float(data.magneticField.z)
            self?.sensorSample.t = Date().millisecondsSince1970
            
            //self?.sensorSample.f = self!.calculateSensorFrequency()
            
              //self?.sensorSample.t = Double(Date().millisecondsSince1970)
            self?.service.receiveUpdate(sensorSample:self!.sensorSample, type:SensorType.magnetometer)
           
            }
           
        }
        
      }
    }
  func stop(){
    MotionManager.shared.manager!.stopMagnetometerUpdates()
  }
  func getValue()->SensorSample{
    return sensorSample;
  }
  
  private func  calculateSensorFrequency()->Double {
         // Initialize the start time.
         if (startTime == 0) {
             startTime = Double(Date().millisecondsSince1970)
         }

         var  timestamp = Double(Date().millisecondsSince1970)

         // Find the sample period (between updates) and convert from
         // nanoseconds to seconds. Note that the sensor delivery rates can
         // individually vary by a relatively large time frame, so we use an
         // averaging technique with the number of sensor updates to
         // determine the delivery rate.
    count+=1
         return (timestamp - startTime) == 0 ? 0:(Double(count) / ((timestamp - startTime) / Double(1000)));
     }

    
}
