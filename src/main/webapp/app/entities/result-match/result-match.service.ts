import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ResultMatch } from './result-match.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ResultMatchService {

    private resourceUrl = 'api/result-matches';

    constructor(private http: Http) { }

    create(resultMatch: ResultMatch): Observable<ResultMatch> {
        const copy = this.convert(resultMatch);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(resultMatch: ResultMatch): Observable<ResultMatch> {
        const copy = this.convert(resultMatch);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ResultMatch> {
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

    private convert(resultMatch: ResultMatch): ResultMatch {
        const copy: ResultMatch = Object.assign({}, resultMatch);
        return copy;
    }
}
