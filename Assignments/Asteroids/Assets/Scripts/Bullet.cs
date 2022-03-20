using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//* @class: Bullet
//* @brief This class controls bullet element
//* @author: Bradley Grose
//* @date: 17/03/2022

public class Bullet: MonoBehaviour {

  //Object Variables
  private Rigidbody2D rb;

  //Constants
  public readonly float LIFETIME = 2.0f;

  //* @summary: Initialize the bullet
  //* @param: none
  //* @return: none
  private void Awake() {
    rb = GetComponent < Rigidbody2D > ();
  }

  //* @summary: Fires the bullet
  //* @param direction: the direction the bullet is fired
  //* @return: none
  public void Fire(Vector2 direction) {
    rb.AddForce(direction * 75000.0f);
    FindObjectOfType < Scene > ().playShoot();
    Destroy(gameObject, LIFETIME);
  }

  //* @summary: Destroys the bullet if collision occurs
  //* @param: collision: the collision that occurs
  //* @return: none
  private void OnCollisionEnter2D(Collision2D collision) {
    Destroy(gameObject);
  }

}