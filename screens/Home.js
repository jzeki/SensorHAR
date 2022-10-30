import * as React from 'react';
import { Text, View, Button } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { GoogleSignin, GoogleSigninButton, statusCodes } from '@react-native-google-signin/google-signin';
import auth from '@react-native-firebase/auth';
import CircularProgress from './CircularProgress';
function HomeScreen({ route, navigation }) {
    /* 2. Get the param */
    const { itemId } = route.params;
    //const { userInfo } = route.params;
    const { loggedIn } = route.params;
    //console.log(JSON.stringify(userInfo.email))

    signOut = async () => {
        try {
          await GoogleSignin.revokeAccess();
          await GoogleSignin.signOut();
          auth()
            .signOut()
            .then(() => {
                //alert('Your are signed out!')
                navigation.navigate('Login')
            });
          //setloggedIn(false);
          // setuserInfo([]);
        } catch (error) {
          console.error(error);
        }
      };
    
    return (
      <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Hero Score</Text>
        <Text></Text>
       
        
        <View>
            <CircularProgress 
             percent={82}
             ringColor="red"
            >

            </CircularProgress>
    
        </View>
      </View>
    );
  }
  
  export default HomeScreen;