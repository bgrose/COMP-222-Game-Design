using System.Collections;
using System.Collections.Generic;
using UnityEngine;


//* @class: Player
//* @brief This class controls the player and its actions
//* @author: Bradley Grose
//* @date: 17/03/2022


public class Player: MonoBehaviour {

  //Objects
  public Bullet bulletPrefab;
  private Rigidbody2D rb;
  private bool isMoving;
  private bool isSlowing;
  private float turnDirection;
  //Constants
  public readonly float THRUSTSPEED = 200.0f;
  public readonly float TURNSPEED = 200.0f;
  //Extras
  public int count = 0;
  public bool vis = true;
  public bool reset = true;

  //* @summary: Initialize the player
  //* @param: none
  //* @return: none
  private void Awake() {
    rb = GetComponent < Rigidbody2D > ();
    FlickerPlayer();
  }

  //* @summary: Gets Keyboard Input and moves the player or shoots
  //* @param: none
  //* @return: none
  private async void Update() {
    //Move forward
    isMoving = Input.GetKey(KeyCode.W);
    //slows down/moves backwards
    isSlowing = Input.GetKey(KeyCode.S);
    //turn left
    if (Input.GetKey(KeyCode.A)) {
      turnDirection = 100.0f;
    }
    //Moves Right
    else if (Input.GetKey(KeyCode.D)) {
      turnDirection = -100.0f;
    }
    //Doesnt Turn
    else {
      turnDirection = 0.0f;
    }
    //Shoot bullet
    if (Input.GetKeyDown(KeyCode.Space)) {
      Shoot();
    }

    // Checks of Asteroids are in the screen
    GameObject[] asteroids = GameObject.FindGameObjectsWithTag("Asteroid");
    //If no asteroids are in the screen, respawn and level up
    if (asteroids.Length == 0 && reset) {
      reset = false;
      FindObjectOfType < GameRunner > ().NextLevel();
      FlickerPlayer();
    }
  }

  //* @summary: Flickers Player of and Off
  //* @param: none
  //* @return: none
  private async void FlickerPlayer() {
    vis = !vis;
    gameObject.GetComponent < SpriteRenderer > ().enabled = vis;
    //Runs a few times and stops
    if (count >= 5) {
      count = 0;
    }
    else {
      Invoke("FlickerPlayer", .25f);
      count++;
    }
  }

  //* @summary: Moves the player
  //* @param: none
  //* @return: none
  private void FixedUpdate() {
    //Propel the player forward
    if (isMoving) {
      rb.AddForce(transform.up * THRUSTSPEED);
    }

    //Slowing and Reversing function
    else if (isSlowing) {
      if (rb.velocity.magnitude > 0.1f) {
        rb.AddForce( - transform.up * THRUSTSPEED);
      } else {
        rb.velocity = Vector2.zero;
      }
    }
    //Turns Player
    if (turnDirection != 0.0f) {
      rb.AddTorque(TURNSPEED * turnDirection);
    }

  }

  //* @summary: Creates a bullet and fires it
  //* @param: none
  //* @return: none
  private void Shoot() {
    Bullet bullet = Instantiate(bulletPrefab, transform.position, transform.rotation);
    bullet.Fire(transform.up);
  }

  //* @summary: Checks for collision with an asteroid
  //* @param collision: the collision that occurs
  //* @return: none
  private void OnCollisionEnter2D(Collision2D collision) {
    if (collision.gameObject.tag == "Asteroid") {
      rb.velocity = Vector2.zero;
      rb.angularVelocity = 0.0f;

      this.gameObject.SetActive(false);

      FindObjectOfType < GameRunner > ().PlayerDied();
    }
  }

  //* @summary: Sets Reset to true
  //* @param: none
  //* @return: none
  public void setReset() {
    reset = true;
  }

  //* @summary: Shows Player Icon
  //* @param: none
  //* @return: none
  public void SetActive() {
    this.gameObject.SetActive(true);
  }

  //* @summary: Sets Positions
  //* @param: none
  //* @return: none
  public void setTransform() {
    transform.position = new Vector3( - 1650.0f, -750.0f, 0);
    transform.rotation = Quaternion.Euler(0, 0, -45);
  }

  //* @summary: Sets Immunity to True
  //* @param: none
  //* @return: none
  public void AddImunity() {
    gameObject.layer = LayerMask.NameToLayer("Immunity");
  }

  //* @summary: Sets Immunity to False
  //* @param: none
  //* @return: none
  public void RemoveImunity() {
    gameObject.layer = LayerMask.NameToLayer("Player");
  }

}