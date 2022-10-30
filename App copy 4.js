import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import LoginScreen from './screens/Login'
import HomeScreen from './screens/Home'
import { Alert, Image, View, Text, TouchableOpacity, SafeAreaView } from 'react-native';
//import CustomMenu from './screens/Menu'
import Menu, {MenuItem, MenuDivider} from 'react-native-material-menu';
const CustomMenu = (props) => {
  let _menu = null;
  return (
    <View style={props.menustyle}>
      <Menu
        ref={(ref) => (_menu = ref)}
        button={
          props.isIcon ? (
            <TouchableOpacity onPress={() => _menu.show()}>
              <Image
                source={{
                  uri:
                    'https://reactnativecode.com/wp-content/uploads/2020/12/menu_icon.png',
                }}
                style={{width: 30, height: 30}}
              />
            </TouchableOpacity>
          ) : (
            <Text
              onPress={() => _menu.show()}
               style={props.textStyle}>
              {props.menutext}
            </Text>
          )
        }>
        <MenuItem onPress={() => {Alert.alert('PopUp Menu Button Clicked...')}}>
          Menu Item 1
        </MenuItem>
 
        <MenuItem disabled>Disabled Menu Item 2</MenuItem>
 
        <MenuDivider />
 
        <MenuItem onPress={() => {Alert.alert('PopUp Menu Button Clicked...')}}>
          Menu Item 3
        </MenuItem>
 
      </Menu>
    </View>
  );
};



const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator
     initialRouteName="Login"
     screenOptions={({route, navigation}) => ({
       headerRight: () => (
         <CustomMenu
           menutext="Menu"
           menustyle={{marginRight: 14}}
           textStyle={{color: 'white'}}
           navigation={navigation}
           route={route}
           isIcon={true}
         />
       ),
     })}
      >
      
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="Home" component={HomeScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
