using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

//* @class: GameRunner
//* @brief This class controls all functions
//*        of the game and its rules
//* @author: Bradley Grose
//* @date: 17/03/2022

public class GameRunner: MonoBehaviour {

  //Objects to reference
  public Player player;
  public ParticleSystem particalSystem;
  public SpawnElements spawnElements;

  //Display Text
  public Text scoreText;
  public Text levelText;
  public Text gameOver;

  //Constants
  public readonly float SPAWNTIME = 5.0f;

  //Score Tracker
  public int score = 0;
  public int level = 1;

  //* @summary: Player Died reset game
  //* @param: none
  //* @return: none
  public void PlayerDied() {
    this.particalSystem.transform.position = player.transform.position;
    this.particalSystem.Play();
    FindObjectOfType < Scene > ().playGameOver();

    GameOver();
  }

  //* @summary: Advances to next level
  //* @param: none
  //* @return: none
  public void NextLevel() {
    //setScore(this.score + 100);
    setLevel(this.level + 1);

    //Ensure All gone (random bug run into)
    GameObject[] asteroids = GameObject.FindGameObjectsWithTag("Asteroid");
    foreach(GameObject asteroid in asteroids) {
      Destroy(asteroid);
    }

    //Destory Bullets
    GameObject[] bullets = GameObject.FindGameObjectsWithTag("Bullet");
    foreach(GameObject bullet in bullets) {
      Destroy(bullet);
    }

    FindObjectOfType < Scene > ().playLevel();
    Respawn();

  
  }

  //* @summary: Respawns the player and objects
  //* @param: none
  //* @return: none
  private void Respawn() {
    //Visual Controls
    gameOver.gameObject.SetActive(false);
    player.gameObject.SetActive(true);

    //Sends different directions
    player.transform.position = new Vector3( - 1650.0f, -750.0f, 0);
    player.transform.rotation = Quaternion.Euler(0, 0, -45);
   
    //Stop all motion on player
    player.GetComponent<Rigidbody2D>().velocity = Vector2.zero;
    player.GetComponent<Rigidbody2D>().angularVelocity = 0.0f;


    spawnElements.Start();
    this.player.gameObject.layer = LayerMask.NameToLayer("Immunity");

    // Wait to remove immunity
    Invoke("removeImmunity", 3.0f);
  }

  //* @summary: Removes Immunity
  //* @param: none
  //* @return: none
  private void removeImmunity() {
    this.player.gameObject.layer = LayerMask.NameToLayer("Player");
  }

  //* @summary: Game Over reset scores and level
  //* @param: none
  //* @return: none
  private void GameOver() {
    this.gameOver.gameObject.SetActive(true);
    //Remove all Astroids
    GameObject[] asteroids = GameObject.FindGameObjectsWithTag("Asteroid");
    foreach(GameObject asteroid in asteroids) {
      Destroy(asteroid);
    }

    // Reset Values
    spawnElements.SPAWNSTARTMIN = 2.0f;
    spawnElements.SPAWNSTARTMAX = 4.0f;

    // Wait for New Game
    Invoke("EndDisplay", SPAWNTIME - 1);
    Invoke("Respawn", SPAWNTIME);

    FindObjectOfType < Scene > ().playLevel();
  }

  //* @summary: Waits to Change Score
  //* @param: none
  //* @return: none
  private void EndDisplay() {
    score = 0;
    level = 1;
    scoreText.text = "Score: " + score;
    levelText.text = "Level: " + level;
  }

  //* @summary: Sets the score
  //* @param: none
  //* @return: none
  public void setScore() {
    score = score + 10 * level;
    scoreText.text = "Score: " + score;
  }

  //* @summary: Sets the level
  //* @param: _level: the level to set
  //* @return: none
  private void setLevel(int _level) {
    level = _level;
    score = score + 100 * level;
    scoreText.text = "Score: " + score;
    levelText.text = "Level: " + level;

  }

  //* @summary: Gets the score
  //* @param: none
  //* @return: the score
  public int getScore() {
    return score;
  }

  //* @summary: Gets the level
  //* @param: none
  //* @return: the level
  public int getLevel() {
    return level;
  }

  //* @summary: Hides Game over Text
  //* @param: none
  //* @return: none
  public void setGameOverOff() {
    gameOver.gameObject.SetActive(false);
  }

}