import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Achievement } from './achievement.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AchievementService {

    private resourceUrl = 'api/achievements';

    constructor(private http: Http) { }

    create(achievement: Achievement): Observable<Achievement> {
        const copy = this.convert(achievement);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(achievement: Achievement): Observable<Achievement> {
        const copy = this.convert(achievement);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Achievement> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(achievement: Achievement): Achievement {
        const copy: Achievement = Object.assign({}, achievement);
        return copy;
    }
}
