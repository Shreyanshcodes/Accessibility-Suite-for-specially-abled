//@dart = 2.9
import 'package:flutter/material.dart';
import 'dart:math' as math;
import 'package:flutter_tts/flutter_tts.dart';

class BndBox extends StatefulWidget {
  final List<dynamic> results;
  final int previewH;
  final int previewW;
  final double screenH;
  final double screenW;

  BndBox(
    this.results,
    this.previewH,
    this.previewW,
    this.screenH,
    this.screenW,
  );

  @override
  _BndBoxState createState() => _BndBoxState();
}

class _BndBoxState extends State<BndBox> {
  @override
  Widget build(BuildContext context) {
    FlutterTts flutterTts = new FlutterTts();

    Future _speak(String object) async {
      var result = await flutterTts.speak(object);
    }

    List<Widget> _renderBox() {
      return widget.results.map((re) {
        var _x = re["rect"]["x"];
        var _w = re["rect"]["w"];
        var _y = re["rect"]["y"];
        var _h = re["rect"]["h"];
        var scaleW, scaleH, x, y, w, h;

        print("the results are as follows " + re["detectedClass"].toString());

        _speak(re["detectedClass"].toString());

        if (widget.screenH / widget.screenW >
            widget.previewH / widget.previewW) {
          scaleW = widget.screenH / widget.previewH * widget.previewW;
          scaleH = widget.screenH;
          var difW = (scaleW - widget.screenW) / scaleW;
          x = (_x - difW / 2) * scaleW;
          w = _w * scaleW;
          if (_x < difW / 2) w -= (difW / 2 - _x) * scaleW;
          y = _y * scaleH;
          h = _h * scaleH;
        } else {
          scaleH = widget.screenW / widget.previewW * widget.previewH;
          scaleW = widget.screenW;
          var difH = (scaleH - widget.screenH) / scaleH;
          x = _x * scaleW;
          w = _w * scaleW;
          y = (_y - difH / 2) * scaleH;
          h = _h * scaleH;
          if (_y < difH / 2) h -= (difH / 2 - _y) * scaleH;
        }

        return Positioned(
          left: math.max(0, x),
          top: math.max(0, y),
          width: w,
          height: h,
          child: Container(
            padding: EdgeInsets.only(top: 5.0, left: 5.0),
            decoration: BoxDecoration(
              border: Border.all(
                color: Color.fromRGBO(37, 213, 253, 1.0),
                width: 3.0,
              ),
            ),
            child: Text(
              "${re["detectedClass"]} ${(re["confidenceInClass"] * 100).toStringAsFixed(0)}%",
              style: TextStyle(
                color: Color.fromRGBO(37, 213, 253, 1.0),
                fontSize: 14.0,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        );
      }).toList();
    }

    return Stack(
      children: _renderBox(),
    );
  }
}
