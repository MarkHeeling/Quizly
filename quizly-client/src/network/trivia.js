import axiosClient from '../apiClient';

export async function getQuestions(){
  return await axiosClient.get('/question/getQuestions');
}

export async function getQuestion(id){
  return await axiosClient.get(`/question/${id}`);
}

export async function createQuestion(data){
  return await axiosClient.post('/question/newQuestion', JSON.stringify(data));
}

export async function deleteQuestion(id){
  return await axiosClient.delete(`/question/deleteQuestion/${id}`);
}

