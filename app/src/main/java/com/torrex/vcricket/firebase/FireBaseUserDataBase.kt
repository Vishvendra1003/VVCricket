package com.torrex.vcricket.firebase

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.torrex.vcricket.activities.mainUi.*
import com.torrex.vcricket.activities.profileUi.ProfileActivity
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.firebase.FirebaseStoreClass.mFireStore
import com.torrex.vcricket.models.User
import com.torrex.vcricket.models.payment.UserFund

class FireBaseUserDataBase {


    //Registering new User to the Database------------------------
    fun registerUser(activity: Activity, user: User){

        mFireStore.collection(DataBaseConstant.USERS)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {

                val userFund=UserFund(user.id,00.00)
                //Add Fund Detail to User
                mFireStore.collection(DataBaseConstant.USER_FUND)
                    .document(userFund.userId)
                    .set(userFund)
                    .addOnSuccessListener {
                        when(activity){
                            is RegisterNewUser->{
                                activity.userRegisteredSuccessfully(user)
                            }
                            is RegisterWithPhoneActivity->{
                                activity.userRegisteredSuccessWithPhone(user)
                            }
                        }
                    }
                    .addOnFailureListener { e->
                        Log.e(activity.javaClass.simpleName,"error while registering the new user from Register User class",e)

                    }
            }

            .addOnFailureListener { e->
                Log.e(activity.javaClass.simpleName,"error while registering the new user from Register User class",e)

            }
    }

    //Get User from the Firebase Store Database
    fun getUserFireStore(activity:Activity,userId:String){
        mFireStore.collection(DataBaseConstant.USERS)
            .document(userId)
            .get()
            .addOnSuccessListener { document->
                Log.v(activity.javaClass.simpleName,document.toString())

                //converting document to user
                val mUser=document.toObject(User::class.java)!!

                when(activity){
                    is LoginActivity->{
                        activity.userLoggedInSuccess(mUser)
                    }
                    is MainActivity->{
                        activity.getUserSuccess(mUser)
                    }
                    is SignInOptionActivity->{
                        activity.getUserSuccess(mUser)
                    }
                    is ProfileActivity->{
                        activity.getUserSuccess(mUser)
                    }
                }
            }
    }

    //Update User Details in the firebase Store Database
    fun updateUserProfile(activity: Activity,userHashMap: HashMap<String,Any>,userId:String){
        mFireStore.collection(DataBaseConstant.USERS)
            .document(userId)
            .update(userHashMap)
            .addOnSuccessListener { document->

                when(activity){
                    is ProfileActivity->{
                        activity.userUpdatedSuccessfully()
                    }
                }
            }
    }

    //Image Upload to cloud
    fun uploadUserImageToCloudStorage(activity: Activity,imageFileURI:Uri,imageType:String){
        val sRef:StorageReference=FirebaseStorage.getInstance().reference
            .child(imageType+System.currentTimeMillis()+"."+
            GlobalFunctions.getFileExtension(activity,imageFileURI))

        sRef.putFile(imageFileURI!!).addOnSuccessListener { taskSnapShot->
            Log.e("Firebase Image URL", taskSnapShot.metadata!!.reference!!.downloadUrl.toString())

            taskSnapShot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { url->
                Log.e("Downloadable LInk",url.toString())

                when(activity){
                    is ProfileActivity->{
                        activity.imageUploadedSuccessfully(url.toString())
                    }
                }

            }
        }
    }
}