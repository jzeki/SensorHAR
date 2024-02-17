import React from 'react'
import { TouchableOpacity, Image, StyleSheet,View } from 'react-native'
import { getStatusBarHeight } from 'react-native-status-bar-height'
import { useState, useEffect,Fragment } from 'react';
//import React, { Fragment } from 'react';
import auth from '@react-native-firebase/auth';
import { GoogleSignin, GoogleSigninButton, statusCodes } from '@react-native-google-signin/google-signin';

export default function GoogleButton({navigation}) {
  const [loggedIn, setloggedIn] = useState(false);
  const [userInfo, setuserInfo] = useState([]);
  const _signIn = async () => {
    try {
        await GoogleSignin.hasPlayServices();
        console.log('has services..')
        const {accessToken, idToken} = await GoogleSignin.signIn();
        console.log('signed ins..')
        setloggedIn(true);
        const credential = auth.GoogleAuthProvider.credential(
          idToken,
          accessToken,
        );
        
        await auth().signInWithCredential(credential);
        console.log('creds..')
        

      } catch (error) {
        if (error.code === statusCodes.SIGN_IN_CANCELLED) {
          // user cancelled the login flow
          alert('Cancel');
        } else if (error.code === statusCodes.IN_PROGRESS) {
          alert('Signin in progress');
          // operation (f.e. sign in) is in progress already
        } else if (error.code === statusCodes.PLAY_SERVICES_NOT_AVAILABLE) {
          alert('PLAY_SERVICES_NOT_AVAILABLE');
          // play services not available or outdated
        } else {
          // some other error happened
        }
      }
  };
  function onAuthStateChanged(user) {
    console.log("auth changed ..")
    setuserInfo(user);
    console.log(user);
    if (user) 
    {
    setloggedIn(true);

    navigation.navigate('Home', {
            itemId: 86,
            userInfo: JSON.stringify(user),
            loggedIn:loggedIn
          });
    }
  }
  useEffect(() => {
    //const subscriber = auth().onAuthStateChanged(onAuthStateChanged);
//return subscriber; // unsubscribe on unmount
    console.log("configure..")
    GoogleSignin.configure({
      //scopes: ['email'],
      webClientId: '937532406737-t88p6tuoiplkoc2rla8f5fqbruk5a04s.apps.googleusercontent.com', 
      //937532406737-t88p6tuoiplkoc2rla8f5fqbruk5a04s.apps.googleusercontent.com
      iosClientId: '937532406737-d061cd6jegu0ho2n2umpieu9h4qptchs.apps.googleusercontent.com',
      offlineAccess: true, 
      //hostedDomain: '', 
      //loginHint: '', 
      //forceConsentPrompt: true, 
      //accountName: '',
      //iosClientId: 'XXXXXX-krv1hjXXXXXXp51pisuc1104q5XXXXXXe.apps.googleusercontent.com'
      });
    // const subscriber = auth().onAuthStateChanged(onAuthStateChanged);
    // return subscriber; // unsubscribe on unmount
  }, []);

  return (
    <View >
        <View style={styles.sectionContainer}>
                <GoogleSigninButton
                  style={{ width: 300, height: 48 }}
                  size={GoogleSigninButton.Size.Wide}
                  color={GoogleSigninButton.Color.Dark}
                  onPress={_signIn}
                   />
              </View>
      </View>
  )
}

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    top: 4 ,
    left: 4,
  },
  image: {
    width: 64,
    height: 24,
  },
})
