import React, { useRef, useEffect } from 'react';
import { SafeAreaView, Alert } from 'react-native';
import Canvas from 'react-native-canvas';

export default function Circles() {
  const ref = useRef(null);

  function handleTouchStart(e) {
    alert('ertert')
    e.preventDefault();
  }

  useEffect(() => {
    if (ref.current) {
      const ctx = ref.current.getContext('2d');

      if (ctx) {
       // Alert.alert('Canvas is ready');

      }
      var canvas = ref.current //document.getElementById('myCanvas');
      var context = canvas.getContext('2d');
      var centerleftX = canvas.width / 4;
      var centerRightX = 3 * canvas.width / 4;
      var centerY = canvas.height / 2;
      var radius = 70;


      canvas.touchstart = function(e) {
        //e.pageX;
        //e.pageY;
        alert('dgdgdf');
    };

      
     
      context.beginPath();
      context.rect(0, 0, 400, 200);
      context.fillStyle = 'rgb(200,200,200)';
      context.fill();
      context.lineWidth = 7;
      context.strokeStyle = 'black';
      context.stroke();
     
     
      context.beginPath();
      context.arc(centerleftX + 50, centerY, radius, 0, 2 * Math.PI, false);
      context.fillStyle = "rgba(0, 0,255,0.7)";
      context.fill();
      
     
     
      context.beginPath();
      context.arc(centerRightX - 50, centerY, radius, 0, 2 * Math.PI, false);
      context.fillStyle = "rgba(255, 0,0,0.7)";
      context.fill();


      context.beginPath();
      context.arc(centerRightX, centerY-50, radius, 0, 2 * Math.PI, false);
      context.fillStyle = "rgba(255, 0,0,0.7)";
      context.fill();

    }
  }, [ref]);

  return (
    <SafeAreaView style={{ flex: 1 }}>
      <Canvas ref={ref} onPress={() => {
    alert('You tapped the button!');
  }}style={{ width: '100%', height: '100%', backgroundColor: 'black' }} />
    </SafeAreaView>
  );
}