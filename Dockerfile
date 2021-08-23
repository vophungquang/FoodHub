FROM python:3.6.8-alpine

WORKDIR /genx

COPY . /genx

ENV CRYPTOGRAPHY_DONT_BUILD_RUST=1

RUN apk add gcc musl-dev python3-dev libffi-dev openssl-dev cargo

RUN pip install --upgrade pip && pip3 install -r requirements.txt
