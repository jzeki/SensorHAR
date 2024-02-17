//
//  TensorFlow.swift
//  RoadsHeroProject
//
//  Created by Zeki on 24.11.22.
//

import Foundation
import AVFoundation
//import FirebaseMLModelDownloader

import TensorFlowLite
class InterpreterStorage:NSObject,AVSpeechSynthesizerDelegate {
  var labels:[String] = ["HOLDING", "STILL"]
  static var sharedInstance = InterpreterStorage()
  var a = [SensorSample]()
  var g = [SensorSample]()
  var m = [SensorSample]()
  var o = [SensorSample]()
  var l = [SensorSample]()
  var N_SAMPLES:Int = 10
  var interpreter:Interpreter?
  let synthesizer = AVSpeechSynthesizer()
  var results:[Float] = [0, 0]
  var i:Int = 0
 
  func stringArrayToData(stringArray: [[[Float]]]) -> Data? {
    return try? JSONSerialization.data(withJSONObject: stringArray, options: [])
  }
  override init(){
    super.init()
    synthesizer.delegate = self
    
    
  }
  func label()->String {
    if (results == nil || results.count == 0) {
                       return "";
                   }
    var max:Float = -1;
    var idx:Int = -1;
    
    print("speak..",results)
    for (index, result) in results.enumerated() {
      if (result > max) {
        idx = index
          max = result;
      }
    }
    return labels[idx]
  }
//  func speak() {
//    
//    
//    if (results == nil || results.count == 0) {
//                       return;
//                   }
//    var max:Float = -1;
//    var idx:Int = -1;
//    
//    print("speak..",results)
//    for (index, result) in results.enumerated() {
//      if (result > max) {
//        idx = index
//          max = result;
//      }
//    }
//    print("speak..",labels[idx])
//    let speech = AVSpeechUtterance(string: labels[idx])
//    synthesizer.speak(speech)
//  }
  func loadModel() {
    // Getting model path
    guard
      let modelPath = Bundle.main.path(forResource: "linear", ofType: "tflite")
     
    else {
      // Error handling...
      print("Failed to load the model file with name")
     return
    }
    do {
    interpreter = try Interpreter(modelPath: modelPath)
      print(" model loaded..")
          } catch let error {
            print(" error..", error)
            // Error handling...
            //print(error)
          }

 }
  
  func predict() {
    i+=1
    print("a count: ", a.count)
    if((a.count<N_SAMPLES)||(g.count<N_SAMPLES)||(o.count<N_SAMPLES)){
      return;
    }

    do {
      var inputData = Data()

      let elementSize = 4
      
      var bytes = [UInt8](repeating: 0, count: elementSize)
      
      for i in 0...9 {
        memcpy(&bytes, &a[i].x, elementSize)
          inputData.append(&bytes, count: elementSize)
        memcpy(&bytes, &a[i].y, elementSize)
          inputData.append(&bytes, count: elementSize)
        memcpy(&bytes, &a[i].z, elementSize)
          inputData.append(&bytes, count: elementSize)

        memcpy(&bytes, &o[i].x, elementSize)
          inputData.append(&bytes, count: elementSize)
        memcpy(&bytes, &o[i].y, elementSize)
          inputData.append(&bytes, count: elementSize)
        memcpy(&bytes, &o[i].z, elementSize)
          inputData.append(&bytes, count: elementSize)

      }

      print("inputData:", inputData.count)


      try interpreter?.allocateTensors()

      try interpreter?.copy(inputData, toInputAt: 0)

//
//      // Run inference by invoking the `Interpreter`.
     // print("to invoke...")
      try interpreter?.invoke()
     // print("after invoke...")
//
//      // Get the output `Tensor`
      let outputTensor = try interpreter?.output(at: 0)
      //print("output: ", outputTensor)

      // Copy output to `Data` to process the inference results.
      let outputSize = (outputTensor?.shape.dimensions.reduce(1, {x, y in x * y}))!
       let outputData =
             UnsafeMutableBufferPointer<Float32>.allocate(capacity: outputSize)
      outputTensor?.data.copyBytes(to: outputData)
      //print("outputData: ", outputData)
      
      
      for (index, element) in outputData.enumerated() {
          //print("labels: ", "\(index): \(element)")
          results[index] = Float(element)
        
      }
//      print("labels: ", results)
//      print("labels: ", "---------------------------")
      
      for i in 0...4 {
        a.remove(at:i)
      }
      

    }catch let error {
      print(" error..", error)
      // Error handling...
      //print(error)
    }
//    if i % 4 == 0 {
//      speak()
//      i = 0
//    }
    
  }
  func downloadModel(){
//    let conditions = ModelDownloadConditions(allowsCellularAccess: false)
//    ModelDownloader.modelDownloader()
//        .getModel(name: "HAR",
//                  downloadType: .localModelUpdateInBackground,
//                  conditions: conditions) { result in
//            switch (result) {
//            case .success(let customModel):
//                do {
//                    // Download complete. Depending on your app, you could enable the ML
//                    // feature, or switch from the local model to the remote model, etc.
//
//                    // The CustomModel object contains the local path of the model file,
//                    // which you can use to instantiate a TensorFlow Lite interpreter.
//                    let interpreter = try Interpreter(modelPath: customModel.path)
//                } catch {
//                    // Error. Bad model file?
//                }
//            case .failure(let error):
//                // Download was unsuccessful. Don't enable ML features.
//                print(error)
//            }
//    }
  }
}
/// Information about a model file or labels file.
typealias FileInfo = (name: String, extension: String)
/// TFLite model types
enum ModelType: CaseIterable {
  case efficientDetLite0
  case efficientDetLite1
  case efficientDetLite2
  case ssdMobileNetV1

  var modelFileInfo: FileInfo {
    switch self {
    case .ssdMobileNetV1:
      return FileInfo("ssd_mobilenet_v1", "tflite")
    case .efficientDetLite0:
      return FileInfo("efficientdet_lite0", "tflite")
    case .efficientDetLite1:
      return FileInfo("efficientdet_lite1", "tflite")
    case .efficientDetLite2:
      return FileInfo("efficientdet_lite2", "tflite")
    }
  }

  var title: String {
    switch self {
    case .ssdMobileNetV1:
      return "SSD-MobileNetV1"
    case .efficientDetLite0:
      return "EfficientDet-Lite0"
    case .efficientDetLite1:
      return "EfficientDet-Lite1"
    case .efficientDetLite2:
      return "EfficientDet-Lite2"
    }
  }
}

/// Default configuration
struct ConstantsDefault {
  static let modelType: ModelType = .efficientDetLite0
  static let threadCount = 1
  static let scoreThreshold: Float = 0.5
  static let maxResults: Int = 3
  static let theadCountLimit = 10
}
