#!/usr/bin/env bash
db.createUser( {
       user:"springbucks",
       pwd:"springbucks",
       roles:[{
             role:"readWrite",
             db:"springbucks" 
             }]

})
