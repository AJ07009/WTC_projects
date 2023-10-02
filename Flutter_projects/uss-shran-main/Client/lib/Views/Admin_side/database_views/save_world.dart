// import 'package:client/Models/DatabaseWorld/database_world_view_model.dart';
// import 'package:flutter/material.dart';
// import 'package:provider/provider.dart';
//
// class SaveWorldDialog extends StatefulWidget {
//   final String ipaddress;
//   final String port;
//   const SaveWorldDialog({Key? key, required this.ipaddress, required this.port}) : super(key: key);
//
//   @override
//   _SaveWorldDialogState createState() => _SaveWorldDialogState();
// }
//
// class _SaveWorldDialogState extends State<SaveWorldDialog> {
//
//   final TextEditingController _worldID = TextEditingController();
//   final _formKey = GlobalKey<FormState>();
//
//
//   @override
//   Widget build(BuildContext context) {
//     return Dialog(
//       child: Form(
//         key: _formKey,
//         child: SizedBox(
//           height: 300,
//           width: 400,
//           child: Column(
//             crossAxisAlignment: CrossAxisAlignment.center,
//             children: [
//               Padding(
//                 padding: const EdgeInsets.all(8.0),
//                 child: Row(
//                   mainAxisAlignment: MainAxisAlignment.end,
//                   children: [
//                     const Text("Saving World", style: TextStyle(fontSize: 20)),
//                     SizedBox(width: MediaQuery.of(context).size.width / 6),
//                     IconButton(
//                         icon: const Icon(Icons.clear),
//                         onPressed: () {
//                           Navigator.of(context).pop();
//                         })
//                   ],
//                 ),
//               ),
//               Row(
//                 children: [
//                   Padding(
//                     padding: const EdgeInsets.all(8.0),
//                     child: Row(
//                       mainAxisAlignment: MainAxisAlignment.center,
//                       children: [
//                         SizedBox(
//                           width: MediaQuery.of(context).size.width / 1.4,
//                           child: TextFormField(
//                             keyboardType: TextInputType.multiline,
//                             minLines: 1,
//                             maxLines: 3,
//                             decoration: const InputDecoration(
//                                 hintText: "Enter a WorldID here"),
//                             controller: _worldID,
//                             validator: (value) {
//                               if (value!.isEmpty) {
//                                 return 'Please enter a WorldID!';
//                               } else {
//                                 return null;
//                               }
//                             },
//                           ),
//                         )
//                       ],
//                     ),
//                   )
//                 ],
//               ),
//               const SizedBox(height: 40),
//               ElevatedButton(
//                   onPressed: () {
//                     if (_formKey.currentState!.validate()) {
//                       // put save logic here
//                       Provider.of<DatabaseWorldViewModel>(context).saveWorld(widget.ipaddress, widget.port, _worldID.value.text);
//                       Navigator.pop(context);
//                       Navigator.pop(context);
//                       // print("Submitting quote");
//                     }
//                   },
//                   child: const Text("Save World")),
//             ],
//           ),
//         ),
//       ),
//     );
//   }
// }
