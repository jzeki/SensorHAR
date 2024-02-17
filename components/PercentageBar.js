import React, { Component } from 'react';
import { View, Text ,TouchableOpacity} from 'react-native';
import { windowHeight, windowWidth } from './Dimensions';

export default class PercentageBar extends Component {
  render() {
    return (
      // Try setting `flexDirection` to 'column'/'column-reverse'/'row'/'row-reverse'
      // <View style={styles.container}>
        <View style={[{ flexDirection:'row', alignItems: 'center'}, styles.elementsContainer]}>
          <TouchableOpacity style={{backgroundColor: '#990011FF',borderTopLeftRadius: 8,borderBottomLeftRadius: 8,alignItems: 'center'}}>
               <Text style={styles.buttonText}>HERO</Text>
          </TouchableOpacity>
          <TouchableOpacity style={{backgroundColor: '#AD2441FF',alignItems: 'center'}}>
               <Text style={styles.buttonText}>Bronze HERO</Text>
          </TouchableOpacity>
          <TouchableOpacity style={{backgroundColor: '#C16871FF',alignItems: 'center'}}>
               <Text style={styles.buttonText}>Silver HERO</Text>
          </TouchableOpacity>
          <TouchableOpacity style={{backgroundColor: '#D59CA1FF',alignItems: 'center'}}>
               <Text style={styles.buttonText}>Gold HERO</Text>
          </TouchableOpacity>
          <TouchableOpacity style={{backgroundColor: '#E9D0D1',alignItems: 'center',borderTopRightRadius: 8,borderBottomRightRadius: 8}}>
               <Text style={styles.buttonText}>Ultimate HERO</Text>
          </TouchableOpacity>
        
          {/* <Text style={{width: windowWidth/5, height: 40, backgroundColor: '#990011FF',color: '#FFFFFF',fontSize: 12,textAlignVertical: "center",textAlign:'center'}} > HERO</Text> */}
          {/* <Text style={{width: windowWidth/5, height: 40, backgroundColor: '#AD2441FF', color: '#e5e5e5',fontSize: 12,textAlignVertical: "center",textAlign:'center'}} >Bronze HERO</Text>
          <Text style={{width: windowWidth/5, height: 40, backgroundColor: '#C16871FF',color: '#b2b2b2',fontSize: 12,textAlignVertical: "center",textAlign:'center'}} >Silver HERO</Text>
          <Text style={{width: windowWidth/5, height: 40, backgroundColor: '#D59CA1FF',color: '#666666',fontSize: 12,textAlignVertical: "center",textAlign:'center'}} >Gold HERO</Text>
          <Text style={{width: windowWidth/5, height: 40, backgroundColor: '#E9D0D1',color: '#000000',fontSize: 12,textAlignVertical: "center",textAlign:'center'}} >Ultimate HERO</Text> */}
        {/* </View>*/}
      </View> 
    );
  }
}

const styles = {
  container: {
    marginTop: 4,
    height:50,
    flex: 1,
   // flexDirection:'row-reverse',
    backgroundColor: 'blue'
  },
  headerStyle: {
    fontSize: 24,
    textAlign: 'center',
    fontWeight: '100',
    marginBottom: 24
  },
  elementsContainer: {
    flex: 1,
   // backgroundColor: '#ecf5fd',
    marginLeft: 5,
    marginRight: 5,
    marginBottom: 5,
    //marginTop: 2
  },
  buttonText: {
    fontSize: 12,
    color: '#ffffff',
    textAlignVertical: "center",
    textAlign:'center',
    width: windowWidth/5, 
    height: 40
  }
}