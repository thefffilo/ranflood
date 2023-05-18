# RanFlood

<img src="icon.png?raw=true" width="33%">

RanFlood consists in two programs: a client and a daemon.

The user interacts with the client to issue commands to the daemon.

The daemon should run as a service (always on, restarted if it crashes, etc.). The daemon accepts as parameter a path to a `setting.ini` file which reports where to store the on-the-fly and shadow-copy DBs and archives and sets its request address (the default is `localhost:7890`). 
The playground folder in this repo includes an [exemplary setting.ini](https://github.com/thesave/ranflood/blob/master/src/tests/java/playground/settings.ini) file.

## Cite this repository

If you use this software in your work, please cite it using the following metadata.

```
@article{BGMMOP23,
  title = {Data Flooding against Ransomware: Concepts and Implementations},
  author = {Davide Berardi and Saverio Giallorenzo and Andrea Melis and Simone Melloni and Loris Onori and Marco Prandini},
  journal = {Computers & Security},
  pages = {103295},
  year = {2023},
  issn = {0167-4048},
  doi = {https://doi.org/10.1016/j.cose.2023.103295},
}
```
