package com.softxpert.petfinder.classes.rest.models.responses

import com.softxpert.petfinder.classes.rest.models.beans.PetBean

data class PetsResponse (var animals: MutableList<PetBean> )
