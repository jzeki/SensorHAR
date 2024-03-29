//
//  GyroscopeSensor.swift
//  DogFoodProject
//
//  Created by Zeki on 14.11.22.
//
import Foundation
class GyroscopeSensor:Sensor{
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
        //print("dispatch..")
        if MotionManager.shared.manager!.isGyroAvailable{
          MotionManager.shared.manager!.gyroUpdateInterval = 0.005
          MotionManager.shared.manager!.startGyroUpdates(to: .main) {
            [weak self] (data, error) in
            guard let data = data, error == nil else {
              return
            }
            
            //self?.sample = self!.newSensorSample();
         // }
            self?.sensorSample.x = Float(data.rotationRate.x)
            self?.sensorSample.y = Float(data.rotationRate.y)
            self?.sensorSample.z = Float(data.rotationRate.z)
            self?.sensorSample.t = Date().millisecondsSince1970
            
            self?.service.receiveUpdate(sensorSample:self!.sensorSample, type:SensorType.gyroscope)
          }
        }
        
      }
    }
  func stop(){
    MotionManager.shared.manager!.stopGyroUpdates()
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

        count+=1
         return (timestamp - startTime) == 0 ? 0:(Double(count) / ((timestamp - startTime) / Double(1000)));
     }

    
}
