import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { GrupoTributacaoImpostoUpdateComponent } from 'app/entities/grupo-tributacao-imposto/grupo-tributacao-imposto-update.component';
import { GrupoTributacaoImpostoService } from 'app/entities/grupo-tributacao-imposto/grupo-tributacao-imposto.service';
import { GrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';

describe('Component Tests', () => {
  describe('GrupoTributacaoImposto Management Update Component', () => {
    let comp: GrupoTributacaoImpostoUpdateComponent;
    let fixture: ComponentFixture<GrupoTributacaoImpostoUpdateComponent>;
    let service: GrupoTributacaoImpostoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [GrupoTributacaoImpostoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GrupoTributacaoImpostoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GrupoTributacaoImpostoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrupoTributacaoImpostoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GrupoTributacaoImposto(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new GrupoTributacaoImposto();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
