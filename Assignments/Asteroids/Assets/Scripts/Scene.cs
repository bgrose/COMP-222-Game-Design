using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//* @class: Scene
//* @brief Runs exit function
//* @author: Bradley Grose
//* @date: 17/03/2022

public class Scene : MonoBehaviour
{

  //Audios
  public AudioSource Shoot;
  public AudioSource Level;
  public AudioSource GameOver;


    
  //* @summary: Quits Game
  //* @param: none
  //* @return: none
  public void QuitGame() {
    Application.Quit();
    Debug.unityLogger.Log("Quit Game");
  }

  public void playShoot() {
    Shoot.Play();
  }

  public void playLevel() {
    Level.Play();
  }

  public void playGameOver() {
    GameOver.Play();
  }


}
