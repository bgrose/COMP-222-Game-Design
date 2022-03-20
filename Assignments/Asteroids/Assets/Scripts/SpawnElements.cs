using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//* @class: SpawnElements
//* @brief This class is the spawner for asteroids
//* @author: Bradley Grose
//* @date: 17/03/2022

public class SpawnElements: MonoBehaviour {

  //Objects
  public Asteroid asteroidPrefab;

  //Constants
  public float SPAWNSTARTMIN = 3;
  public float SPAWNSTARTMAX = 5;

  //* @summary: Initialize the spawner
  //* @param: none
  //* @return: none
  public void Start() {
    FindObjectOfType < GameRunner > ().setGameOverOff();
    int level = FindObjectOfType < GameRunner > ().getLevel();

    //Sets Asteroid Spawn Rate for each level
    SPAWNSTARTMAX = SPAWNSTARTMAX + ((level - 1) * 2);
    SPAWNSTARTMIN = SPAWNSTARTMIN + ((level - 1) * 2);

    //Spawns Asteroids
    for (int i = 0; i < Random.Range(SPAWNSTARTMIN, SPAWNSTARTMAX); i++) {
      Spawn();
    }
    FindObjectOfType < Player > ().setReset();
  }

  //* @summary: Spawns new asteroid
  //* @param: none
  //* @return: none
  private void Spawn() {

    Vector3 position = createCords();
    float angle = Random.Range(0.0f, 360.0f);
    Quaternion rotation = Quaternion.AngleAxis(angle, Vector3.forward);
    Asteroid asteroid = Instantiate(asteroidPrefab, position, rotation);
    asteroid.size = Random.Range(asteroid.MINSIZE, asteroid.MAXSIZE);
    asteroid.setAngle(rotation * position);

  }

  //* @summary: Creates split asteroid
  //* @param: position: the position of the asteroid
  //* @param: size: the size of the asteroid
  //* @param: angle: the angle of the asteroid
  public void Spawn(Vector3 position, float angle, float size) {
    Quaternion rotation = Quaternion.AngleAxis(angle, Vector3.forward);
    Asteroid asteroid = Instantiate(asteroidPrefab, position, rotation);
    asteroid.size = size;
    asteroid.setAngle(rotation * position);
  }

  //* @summary: Creates a random position for the asteroid not by player
  //* @param: none
  //* @return: none
  private Vector3 createCords() {
    float x = Random.Range( - 1700.0f, 1700.0f);
    float y = Random.Range( - 800.0f, 800.0f);

    //Checks for spawn in player radius
    if (x < -1500.0f && y < -600.0f) {
      return createCords();
    } else {
      return new Vector3(x, y, 0);
    }
  }

}