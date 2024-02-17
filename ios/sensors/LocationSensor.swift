//
//  LocationSensor.swift
//  DogFoodProject
//
//  Created by Zeki on 14.11.22.
//
import Foundation
import CoreLocation

//class LocationSensor: NSObject, CLLocationManagerDelegate{
class LocationSensor: NSObject, Sensor, CLLocationManagerDelegate{
  var sensorSample = SensorSample()
  private var service : SensorService
  let id: Int
  var locationManager:CLLocationManager!
  init(service : SensorService, id:Int) {
      self.id = id
      self.service = service
      super.init()
      self.service.register(sensor: self)
    
      locationManager = CLLocationManager()
          locationManager.desiredAccuracy = kCLLocationAccuracyKilometer
          locationManager.requestWhenInUseAuthorization()
          //print("location..");
          
          locationManager.delegate = self
      
      
        
    }
  
  func locationManager(_ manager: CLLocationManager,
                                didUpdateLocations locations: [CLLocation]){
      
      if let newLocation = locations.last{
        //Counter.sharedInstance.updateLocation(location:newLocation);
          //print("location..");
        //self.sensorSample.t = Double(Date().millisecondsSince1970)
        self.sensorSample.x = Float(newLocation.coordinate.latitude)
        self.sensorSample.y = Float(newLocation.coordinate.longitude)
        self.sensorSample.z = Float(newLocation.speed)
        self.sensorSample.t = newLocation.timestamp.millisecondsSince1970
        print("location update")
        self.service.receiveUpdate(sensorSample:self.sensorSample,type:SensorType.location)
          //print("(\(newLocation.coordinate.latitude), \(newLocation.coordinate.latitude))")
      }
      
  }
  
  func start(){
    //print("location enabled: ", CLLocationManager.locationServicesEnabled())
    if CLLocationManager.locationServicesEnabled() {
        locationManager.startUpdatingLocation()
        //locationManager.startUpdatingHeading()
    }
  }
  func stop(){
    if CLLocationManager.locationServicesEnabled() {
      locationManager.stopUpdatingLocation()
        //locationManager.startUpdatingHeading()
    }
  }
}
