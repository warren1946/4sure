package net.foursure.hogwarts.utils

import org.json.JSONObject

class ApiRequestHelper (val url: String, val result: (JSONObject) -> Unit, val error: (String) -> Unit){

}