# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.contrib.auth.hashers import make_password, check_password
from django.views.decorators.csrf import csrf_exempt
from django.http import JsonResponse
from models import UserModel
import json

@csrf_exempt
def SignupView(request):
    print request.body
    if request.method == 'POST':
        data = json.loads(request.body.decode(encoding='UTF-8'))
        name = data['name']
        username = data['username']
        email = data['email']
        password = make_password(data['password'])
        user = UserModel(username=username, name=name, password=password, email=email)
        print user
        user.save()
        resp = {
            'code' : 200,
        }
    else:
        resp = {
            'code' : 400,
        }

    return JsonResponse(resp, safe=False)

@csrf_exempt
def LoginView(request):
    if request.method == 'POST':
        print request.body
        data = json.loads(request.body.decode(encoding='UTF-8'))
        username = data['username']
        password = data['password']
        user = UserModel.objects.filter(username=username).first()

        if check_password(password, user.password):
            resp = {
                'code' : 200,
            }
    else:
        resp = {
            'code' : 400,
        }

    return JsonResponse(resp, safe=False)

