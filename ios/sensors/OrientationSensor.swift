//
//  GyroscopeSensor.swift
//  DogFoodProject
//
//  Created by Zeki on 14.11.22.
//
import Foundation
import CoreMotion
class OrientationSensor:Sensor{
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
        MotionManager.shared.manager!.deviceMotionUpdateInterval = 0.01
        MotionManager.shared.manager!.startDeviceMotionUpdates(using: CMAttitudeReferenceFrame.xMagneticNorthZVertical, to: .main) { [weak self](data: CMDeviceMotion?, error: Error?) in
            guard let data = data else {
                print("Error: \(error!)")
                return
            }

            let motion: CMAttitude = data.attitude
          //self?.sensorSample = self!.newSensorSample();
        //  self?.manager.deviceMotionUpdateInterval = 2.5

            DispatchQueue.main.async {
               
  //            self?.sample.ox = motion.pitch
  //            self?.sample.oy = motion.yaw
  //            self?.sample.oz = motion.roll
              //self?.sensorSample.t = Double(Date().millisecondsSince1970)
              self?.sensorSample.x = Float(motion.yaw)
              self?.sensorSample.y = Float(motion.pitch)
              self?.sensorSample.z = Float(motion.roll)
              self?.sensorSample.t = Date().millisecondsSince1970
              
              //self?.sensorSample.f = self!.calculateSensorFrequency()
              
              self?.service.receiveUpdate(sensorSample:self!.sensorSample,type:SensorType.orientation)
            }
        }
        
      }
    }
  func stop(){
    MotionManager.shared.manager!.stopDeviceMotionUpdates()
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
