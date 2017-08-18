# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from Registration.models import UserModel
# Create your models here.

class QueryModel(models.Model):
    key = models.CharField(max_length=120)
    query = models.CharField(max_length=10000)
    created_on = models.DateTimeField(auto_now_add=True)
    updated_on = models.DateTimeField(auto_now=True)


class TakeupModel(models.Model):
    user = models.ForeignKey(UserModel)
    query = models.ForeignKey(QueryModel)
    created_on = models.DateTimeField(auto_now_add=True)
    updated_on = models.DateTimeField(auto_now=True)

