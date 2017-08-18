# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.

class UserModel(models.Model):
    username = models.CharField(max_length=120, primary_key=True)
    password = models.CharField(max_length=40)
    points = models.IntegerField(default=0)

