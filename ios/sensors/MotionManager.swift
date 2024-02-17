//
//  MotionManager.swift
//  DogFoodProject
//
//  Created by Zeki on 14.11.22.
//

import Foundation
import CoreMotion
class MotionManager{
    
  static let shared = MotionManager();
  var manager:CMMotionManager?;
    //Initializer access level change now
    private init(){
      manager = CMMotionManager()
    }
   
    
}
