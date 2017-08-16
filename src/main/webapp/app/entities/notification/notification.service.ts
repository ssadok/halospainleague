import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Notification } from './notification.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class NotificationService {

    private resourceUrl = 'api/notifications';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(notification: Notification): Observable<Notification> {
        const copy = this.convert(notification);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(notification: Notification): Observable<Notification> {
        const copy = this.convert(notification);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Notification> {
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
        entity.creation = this.dateUtils
            .convertDateTimeFromServer(entity.creation);
    }

    private convert(notification: Notification): Notification {
        const copy: Notification = Object.assign({}, notification);

        copy.creation = this.dateUtils.toDate(notification.creation);
        return copy;
    }
}
