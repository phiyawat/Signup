package com.example.phiyawat.loginsystem

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import org.apache.http.HttpClientConnection
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.message.BufferedHeader

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList



class RegisterActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val bt_signup = findViewById(R.id.bSignup) as Button
        bt_signup.setOnClickListener {
            val task = WebPageTask()
            task.execute()
        }

    }

    fun signUp(): String? {
        val etUsername = findViewById(R.id.etUserName) as EditText
        val etPassword = findViewById(R.id.etPassword) as EditText
        val etFirstname = findViewById(R.id.etFirstname) as EditText
        val etLastname = findViewById(R.id.etLastname) as EditText
        val etEmail = findViewById(R.id.etEmail) as EditText
        val nameValuePairs = ArrayList<NameValuePair>(2)
        nameValuePairs.add(BasicNameValuePair("username", etUsername.text.toString()))
        nameValuePairs.add(BasicNameValuePair("password", etPassword.text.toString()))
        nameValuePairs.add(BasicNameValuePair("firstname", etFirstname.text.toString()))
        nameValuePairs.add(BasicNameValuePair("lastname", etLastname.text.toString()))
        nameValuePairs.add(BasicNameValuePair("email", etEmail.text.toString()))
        return postData(url, nameValuePairs)
    }

    fun postData(url: String, nameValuePairs: List<NameValuePair>): String? {
        val httpclient = DefaultHttpClient()
        val httppost = HttpPost(url)
        try {
            httppost.setEntity(UrlEncodedFormEntity(nameValuePairs))
            val response = httpclient.execute(httppost)
            val bf = BufferedReader(InputStreamReader(response.getEntity().getContent()))
            val sb = StringBuffer("")
            val line = mutableListOf<String>()
            bf.useLines { lines -> lines.forEach { line.add(it) } }
            line.forEach { println(">  " + it) }
            bf.close()
            val page = sb.toString()
            return page
        } catch (e: ClientProtocolException) {
        } catch (e: IOException) {
        }

        return null
    }

    inner class WebPageTask : AsyncTask<Void, Void, String>() {
        override fun onPostExecute(result: String) {
            if (result.trim { it <= ' ' } == "OK") { //data success but show Sign up fail
                Toast.makeText(this@RegisterActivity, "Sign up Success", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@RegisterActivity, "Sign up fail", Toast.LENGTH_LONG).show()
                print("a:"+result)
            }
        }

        override fun doInBackground(vararg param: Void): String {
            result = signUp() as String
            return result
        }

    }

    companion object {
        val url = "http://192.168.1.100/www/signup.php"
        var result = ""
    }
}
