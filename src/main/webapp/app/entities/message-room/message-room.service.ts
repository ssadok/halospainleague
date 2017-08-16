import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { MessageRoom } from './message-room.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MessageRoomService {

    private resourceUrl = 'api/message-rooms';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(messageRoom: MessageRoom): Observable<MessageRoom> {
        const copy = this.convert(messageRoom);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(messageRoom: MessageRoom): Observable<MessageRoom> {
        const copy = this.convert(messageRoom);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<MessageRoom> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.crated = this.dateUtils
            .convertDateTimeFromServer(entity.crated);
    }

    private convert(messageRoom: MessageRoom): MessageRoom {
        const copy: MessageRoom = Object.assign({}, messageRoom);

        copy.crated = this.dateUtils.toDate(messageRoom.crated);
        return copy;
    }
}
