//
//  UIService.m
//  SampleApp
//
//  Created by Zeki on 30.9.22.
//

#import <Foundation/Foundation.h>
#import "React/RCTBridgeModule.h"
#import "React/RCTEventEmitter.h"

@interface RCT_EXTERN_MODULE(UIService, RCTEventEmitter)
RCT_EXTERN_METHOD(passValueFromReact:(NSString *)value)
//RCT_EXTERN_METHOD(decrement: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject)
@end
