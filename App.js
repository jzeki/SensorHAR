import Background from './components/Background'
import { windowHeight, windowWidth } from './components/Dimensions';
import {NativeEventEmitter,DeviceEventEmitter,NativeModules} from 'react-native';
import React, { useState,useEffect }  from 'react';
import { Text, View,StyleSheet} from 'react-native';

function App() {
  //const YourModule = NativeModules.UIService;
  const { UIService } = NativeModules
  const [stat, setStat] = useState("");
 
  const onStatus = (event) => {
    console.log(event);
   //status=event.status;
    setStat(event.status)
  }
  useEffect(() => {
    //handleClick
    if(Platform.OS=='android'){
      DeviceEventEmitter.addListener('onStatus', onStatus);
      NativeModules.DeviceModule.startSending((err, name) => 
      {
        console.log(err, name);
        //setName(name);
      });
    }
    if(Platform.OS=='ios'){
      const CounterEvents = new NativeEventEmitter(NativeModules.UIService)
      CounterEvents.addListener('onStatus', onStatus);
      UIService.passValueFromReact("Hello World")
      
    }
   
  
   }, [])

    return (
    <>
      
      <Background>
     
   
        <View style={styles.circleA}  >
                <Text style={styles.text}>
                    {stat}
                </Text>
           </View>
       
    
      </Background>
    </>
    );
  }

  const styles = StyleSheet.create({
    container: {
      //flex: 1,
     // marginTop:50,
      //justifyContent: 'center',
      //alignItems: 'center',
      backgroundColor: '#EDFC8D',
      width:windowWidth,
      //height:2*windowHeight
    },
    circleA: {
      backgroundColor: "#C16871FF",
      borderRadius: 200,
      width: windowWidth/2,
      height: windowWidth/2,
      alignItems:'center', 
      justifyContent:'center'
      //position:"center",
     
  },
  
  text: {
    color: "white",
    fontSize:20,
  },
  title: {
    margin: 20,
    fontSize:18,
  },
    
    
    });
  export default App;
