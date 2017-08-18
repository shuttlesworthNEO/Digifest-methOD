# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.http import JsonResponse
from Registration.models import UserModel
import json
from django.views.decorators.csrf import csrf_exempt
from models import QueryModel, TakeupModel
from django.db.models import Q
import simplejson
from django.http import HttpResponse

@csrf_exempt
def QueryView(request):
    if request.method == 'POST':
        print request.body
        data = json.loads(request.body.decode(encoding='UTF-8'))
        key = data['id']
        query = data['text']
        query_object = QueryModel(key=key, query=query)
        query_object.save()
        resp = {
            'id' : key,
            'text' : query
        }
    else:
        resp = {
            'code' : 400,
        }

    return JsonResponse(resp, safe=False)

def FeedView(request):
    if request.method == 'GET':
        print request.body
        query_obj = QueryModel.objects.all()
        query = []
        for x in query_obj:
            temp = {
                'id' : x.key,
                'text' : x.query,
            }
            query.append(temp)

        resp = json.dumps(query)
        print resp
        return JsonResponse(json.loads(resp), safe=False)

@csrf_exempt
def ResolvedView(request):
    if request.method == 'POST':
        print request.body
        data = json.loads(request.body.decode(encoding='UTF-8'))
        username = data['username']
        query = data['queryId']
        bool = data['resolved']
        user = UserModel.objects.get(username=username)
        if(bool == '1'):
            user.points += 10
            user.save()
            QueryModel.objects.filter(key=query).delete()
        resp = {
            'username' : username,
            'queryID' : query,
            'resolved' : bool
        }
    else:
        resp = {
            'code' : 400,
        }
    return JsonResponse(resp, safe=False)

@csrf_exempt
def UserView(request):
    resp = {}
    if request.method == "POST":
        data = json.loads(request.body.decode(encoding='UTF-8'))
        username = data['username']
        user = UserModel.objects.filter(username=username).first()
        resp = {
            'score' : user.points,
            'username' : username
        }
    return JsonResponse(resp, safe=False)


def LeaderView(request):
    if request.method == "GET":
        user = UserModel.objects.order_by('-points').all()
        temp = []
        for x in user:
            d = {
                'username' : x.username,
                'score' : x.points,
            }
            temp.append(d)
        return render(request, "leaderboard.html", {'data' : temp})

