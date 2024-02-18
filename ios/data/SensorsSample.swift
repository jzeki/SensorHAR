//
//  SensorSample.swift
//  SampleApp
//
//  Created by Zeki on 11.10.22.
//

import Foundation
struct SensorsSample: Codable,ReflectedStringConvertible {
  var a:SensorSample?
  var g:SensorSample?
  var m:SensorSample?
  var o:SensorSample?
  var t:Double = 0
//  init(a:SensorSample? = nil,g:SensorSample? = nil,m:SensorSample? = nil,o:SensorSample? = nil){
//    self.a=a
//    self.g=g
//    self.m=m
//    self.o=o
//  }
}
