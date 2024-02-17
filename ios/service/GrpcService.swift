//
//  GrpcService.swift
//  DogFoodProject
//
//  Created by Zeki on 15.11.22.
//

import Foundation
@objc(GrpcService)
class GrpcService: RCTEventEmitter {
  static var sharedInstance:GrpcService! = GrpcService.init()
  
  
  
  override init() {
    //var accSensor = AccelerationSensor(sharedInstance:SensorService())
    super.init()
    //Counter.sharedInstance = self
  }
  
 

 
  
  
}
