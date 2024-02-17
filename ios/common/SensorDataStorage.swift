//
//  SensorDataStorage.swift
//  SampleApp
//
//  Created by Zeki on 11.10.22.
//

import Foundation
class SensorDataStorage: Codable{
  static var sharedInstance = SensorDataStorage()

  var Data = [SensorsSample]()
  //var Data = SynchronizedArray<SensorsSample>()
  var Time:Double=0
  var TripId:Double=0
  var Tbt:Int = 0
}
