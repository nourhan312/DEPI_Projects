import 'package:flutter/material.dart';
import 'package:task1/login.dart';
import 'package:task1/signin.dart';

class Signin extends StatelessWidget {
  const Signin({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        automaticallyImplyLeading: false,
        backgroundColor: Colors.blue,
        title: const Text(
          "Signin Page",
          style: TextStyle(
            color: Colors.white,
            fontSize: 20,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset(
                'assets/images/flutter.jpeg',
                width: 350,
                height: 200,
              ),
              Container(
                decoration: BoxDecoration(
                    border: Border.all(
                  color: Colors.blue,
                  width: 1,
                )),
                child: TextFormField(
                  keyboardType: TextInputType.emailAddress,
                  decoration: const InputDecoration(
                      contentPadding: EdgeInsets.all(8),
                      hintText: 'Name',
                      label: Text("Name"),
                      border: InputBorder.none),
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              Container(
                decoration: BoxDecoration(
                    border: Border.all(
                  color: Colors.blue,
                  width: 1,
                )),
                child: TextFormField(
                  keyboardType: TextInputType.emailAddress,
                  decoration: const InputDecoration(
                      contentPadding: EdgeInsets.all(8),
                      hintText: 'Email',
                      label: Text("Email"),
                      border: InputBorder.none),
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              Container(
                decoration: BoxDecoration(
                  border: Border.all(
                    color: Colors.blue,
                    width: 1,
                  ),
                ),
                child: TextFormField(
                  obscureText: true,
                  decoration: const InputDecoration(
                      hintText: "Password",
                      contentPadding: EdgeInsets.all(8),
                      label: Text("Password"),
                      border: InputBorder.none),
                ),
              ),
              const SizedBox(
                height: 30,
              ),
              ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue,
                    minimumSize: const Size.fromHeight(50),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                  onPressed: () {
                    Navigator.push(context, MaterialPageRoute(builder: (_) {
                      return const Signin();
                    }));
                  },
                  child: const Text('Signin',
                      style: TextStyle(fontSize: 20, color: Colors.white))),
              SizedBox(
                height: 20,
              ),
              InkWell(
                onTap: () {
                  Navigator.push(context, MaterialPageRoute(builder: (_) {
                    return const Login();
                  }));
                },
                child: const Text.rich(
                  TextSpan(children: [
                    TextSpan(
                      text: ' Already have account ? ',
                      style: TextStyle(
                        color: Colors.blue,
                        fontSize: 15,
                        fontWeight: FontWeight.w500,
                      ),
                    ),
                    TextSpan(
                      text: 'Login here',
                      style: TextStyle(
                        color: Colors.black87,
                        fontSize: 15,
                        fontWeight: FontWeight.w500,
                      ),
                    ),
                  ]),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
