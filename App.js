import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import LoginScreen from './screens/Login'
import HomeScreen from './screens/Home'
import { Alert, Image, View, Text, Button, TouchableOpacity, SafeAreaView,DrawerButton,LogoTitle } from 'react-native';
//import CustomMenu from './screens/Menu'
import MenuExample from './screens/Menu';

const Stack = createStackNavigator();

export default function App() {
  return (
    <NavigationContainer
    
    >
      <Stack.Navigator 
      
        >
      <Stack.Screen name="Login" component={LoginScreen}
      options={{
        headerShown: false
      }}
       />
      <Stack.Screen
        name="Home"
        component={HomeScreen}
        options={({route, navigation}) => ({
          title: 'Welcome',
          headerLeft: ()=> null,
          headerRight: () => (
            <MenuExample
            navigation={navigation}
            />
            ),
          })}
      />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
