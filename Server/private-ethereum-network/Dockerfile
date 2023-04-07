# 도커 허브에서 official geth image 사용
FROM ethereum/client-go:v1.10.1

ARG ACCOUNT_PASSWORD

# 이미지 안으로 genesis.json 복사
COPY genesis.json /tmp

# genesis.json 실행, nodekey 파일 삭제
RUN geth init /tmp/genesis.json \
    && rm -f ~/.ethereum/geth/nodekey \
    && echo ${ACCOUNT_PASSWORD} > /tmp/password \
    && geth account new --password /tmp/password \
    && rm -f /tmp/password

ENTRYPOINT ["geth"]