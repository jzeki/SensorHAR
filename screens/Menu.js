import React, { useState } from 'react';
import { View, StyleSheet, Alert } from 'react-native';
import { Button, Menu, Provider,List  } from 'react-native-paper';
import { GoogleSignin, GoogleSigninButton, statusCodes } from '@react-native-google-signin/google-signin';
import auth from '@react-native-firebase/auth';
  
const MenuExample = ({ route, navigation }) => {
  const [visible, setVisible] = useState(false);
  
  const closeMenu = () => setVisible(false);
  const openMenu = (v) => setVisible(true);

  const signOut = async () => {
    try {
      await GoogleSignin.revokeAccess();
      await GoogleSignin.signOut();
      auth()
        .signOut()
        .then(() => {
            //alert('Your are signed out!')
            navigation.navigate('Login')
            setVisible(false)
        });
       
      //setloggedIn(false);
      // setuserInfo([]);
    } catch (error) {
      console.error(error);
    }
  };

  
  return (
    <Provider>
      <View style={{flex:1,marginTop:20}}>
          <Menu
            visible={visible}
            onDismiss={closeMenu}
            style={{width:'100%'}}
            anchor={<Button onPress={openMenu}>Show menu</Button>}
            >
            <Menu.Item onPress={closeMenu} title="Profile" />
            <Menu.Item onPress={signOut} title="Log Out" />
            {/* <List.Item onPress={() => {}} title="Item 2" />
            <List.Item onPress={() => {}} title="Item 3" /> */}
          </Menu>
      </View>
    </Provider>
  );
};
  
export default MenuExample;
  
// const styles = StyleSheet.create({
//   container: {
//     padding: 50,
//     flexDirection: 'row',
//     justifyContent: 'center',
//     height: 200,
//   },
// });