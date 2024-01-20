package com.dinesh.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        com.dinesh.android.v0.DI().main()
//        com.dinesh.dagger.v0.constructor.DI().main()
//        com.dinesh.dagger.v0.field.DI().main()
//        com.dinesh.dagger.v0.method.DI().main()
//        com.dinesh.dagger.v0.constructor_field.DI().main()
//        com.dinesh.dagger.v0.full.DI().main()

//        com.dinesh.dagger.v1.constructor.DI().main()
//        com.dinesh.dagger.v1.constructor_method.DI().main()
//        com.dinesh.dagger.v1.constructor_field.DI().main()

        startActivity(Intent(this, com.dinesh.hilt.v2.Main::class.java))
//        startActivity(Intent(this, com.dinesh.hilt.basic.named.Main::class.java))
    }
}
