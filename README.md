[![Build Status](https://travis-ci.org/saymagic/Amberjack.svg?branch=master)](https://travis-ci.org/saymagic/Amberjack)

![](img/stick_pic.png)

MDocker base on Docker Remote API, gets image and container's info from docker server.

# User Interface

<img src="img/ui_1.png" width="340" />
<img src="img/ui_2.png" width="340" />

<img src="img/ui_3.png" width="340" />

# Use-pattern

* Firstly, you must have a server which has installed Docker.
* Secondly, you should modify `/etc/default/docker` file, and add TCP connection mode to DOCKER_OPTS. The file maybe empty, so you also need add unix-sock mode, like this:

	 DOCKER_OPTS="-H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock"
* Restart docker.
	
	 sudo service docker restart

* Finally, test on your server: 
	
	curl http://localhost:2375/info

If response looks like below , you are ready!

![](img/image_20160303102658.png)

Try to login with your IP and Port! Have Fun!

#Test Running

> gradle installOtherDebug

#Download:

[Google Play]()

[http://fir.im/docker](http://fir.im/docker)

#Thanks 
>* [RxJava](https://github.com/ReactiveX/RxJava)
>* [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
>* [Dagger](https://github.com/square/dagger)
>* [Glide](https://github.com/bumptech/glide)
>* [Butter Knife](https://github.com/JakeWharton/butterknife)
>* [Retrofit](https://github.com/square/retrofit)
>* [OkHttp](https://github.com/square/okhttp)
>* [LeakCanary](https://github.com/square/leakcanary)
>* [Logger](https://github.com/orhanobut/logger)
>* [Stetho](https://github.com/facebook/stetho)

# About me

saymagic.dev@gmail.com

# LICENSE

   Copyright 2017 Saymagic

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
