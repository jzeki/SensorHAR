import React, { useState, useEffect,Fragment } from 'react';
import auth from '@react-native-firebase/auth';
import { SafeAreaView, StyleSheet, ScrollView, View, Text, StatusBar, Button, Image,} from 'react-native';
import { Header, LearnMoreLinks, Colors, DebugInstructions, ReloadInstructions,} from 'react-native/Libraries/NewAppScreen';


//import React, { Fragment } from 'react';
import { GoogleSignin, GoogleSigninButton, statusCodes } from '@react-native-google-signin/google-signin';


function App() {
  
  // Set an initializing state whilst Firebase connects
 
  // state = {
  //   userInfo: [],
  //   loggedIn: false
  // }
  const [loggedIn, setLoggedIn] = useState(false);
  const [userInfo, setUserInfo] = useState({});

  // const start = () => {
  //   console.log("start..");
  //   NativeModules.Counter.start()
  // };
const _signIn = async () => {
  console.log("sign in..")
  try {
    console.log("check play..")
    await GoogleSignin.hasPlayServices();
    console.log("true play..")
    //const userInfo = await GoogleSignin.signIn();
    GoogleSignin.signIn().then((UserInfo) => {
      console.log(JSON.stringify(UserInfo))
      setLoggedIn(true)
      setUserInfo(userInfo => ({
        ...userInfo,
        ...UserInfo
      }));
      }).catch((e) => {
      console.log("ERROR IS: " + JSON.stringify(e));
      })

    console.log("user info..",userInfo)
    //setState({ userInfo: userInfo, loggedIn: true });
   
  } catch (error) {
    if (error.code === statusCodes.SIGN_IN_CANCELLED) {
      // user cancelled the login flow
    } else if (error.code === statusCodes.IN_PROGRESS) {
      // operation (f.e. sign in) is in progress already
    } else if (error.code === statusCodes.PLAY_SERVICES_NOT_AVAILABLE) {
      // play services not available or outdated
    } else {
      // some other error happened
    }
  }
};
const getCurrentUserInfo = async () => {
  try {
    const userInfo = await GoogleSignin.signInSilently();
    //this.setState({ userInfo });
  } catch (error) {
    if (error.code === statusCodes.SIGN_IN_REQUIRED) {
      // user has not signed in yet
      //this.setState({ loggedIn: false });
    } else {
      // some other error
     // this.setState({ loggedIn: false });
    }
  }
};
const signOut = async () => {
  try {
    await GoogleSignin.revokeAccess();
    await GoogleSignin.signOut();
    setLoggedIn(false)
    //this.setState({ user: null, loggedIn: false }); // Remember to remove the user from your app's state as well
  } catch (error) {
    console.error(error);
  }
};



  useEffect(() => {
    console.log("configure..")
    GoogleSignin.configure({
      //scopes: [], 
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
    <Fragment>
        <SafeAreaView>
          <ScrollView
            contentInsetAdjustmentBehavior="automatic"
            style={styles.scrollView}>
            <Header />
          
            <View style={styles.body}>
              <View style={styles.sectionContainer}>
                <GoogleSigninButton
                  style={{ width: 192, height: 48 }}
                  size={GoogleSigninButton.Size.Wide}
                  color={GoogleSigninButton.Color.Dark}
                  onPress={_signIn}
                   />
              </View>
              <View style={styles.buttonContainer}>
                {!loggedIn && <Text>You are currently logged out</Text>}
                {loggedIn && <Button onPress={signOut}
                  title="Signout"
                  color="#841584">
                </Button>}
              </View>

              {loggedIn && <View>
                <View style={styles.listHeader}>
                  <Text>User Info</Text>
                </View>
                <View style={styles.dp}>
                  <Image
                    style={{ width: 100, height: 100 }}
                    source={{ uri: userInfo && userInfo.user && userInfo.user.photo }}
                  />
                </View>
                <View style={styles.detailContainer}>
                  <Text style={styles.title}>Name</Text>
                  <Text style={styles.message}>{userInfo && userInfo.user && userInfo.user.name}</Text>
                </View>
                <View style={styles.detailContainer}>
                  <Text style={styles.title}>Email</Text>
                  <Text style={styles.message}>{userInfo && userInfo.user && userInfo.user.email}</Text>
                </View>
                <View style={styles.detailContainer}>
                  <Text style={styles.title}>ID</Text>
                  <Text style={styles.message}>{userInfo && userInfo.user && userInfo.user.id}</Text>
                </View>
              </View>}
            </View>
          </ScrollView>
        </SafeAreaView>
      </Fragment>
  );

 
}
const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  listHeader: {
    backgroundColor: '#eee',
    color: "#222",
    height: 44,
    padding: 12
  },
  detailContainer: {
    paddingHorizontal: 20
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
    paddingTop: 10
  },
  message: {
    fontSize: 14,
    paddingBottom: 15,
    borderBottomColor: "#ccc",
    borderBottomWidth: 1
  },
  dp:{
    marginTop: 32,
    paddingHorizontal: 24,
    flexDirection: 'row',
    justifyContent: 'center'
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
    flexDirection: 'row',
    justifyContent: 'center'
  },
  buttonContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
    flexDirection: 'row',
    justifyContent: 'center'
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
  });
export default App;
