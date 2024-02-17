import Foundation

public extension Date {
    
    var millisecondsSince1970: Int64 {
        return timeIntervalSince1970.milliseconds
    }
    
    init(milliseconds: Int) {
        self = Date(timeIntervalSince1970: TimeInterval(milliseconds / 1000))
    }
    
}

public extension TimeInterval {
    
    var milliseconds: Int64 {
        return Int64((self * 1000.0).rounded())
    }
    
}
