//Antonio D. Cavan
//WFUV

#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>

typedef struct{
	int index;
	char c;
	int frqncy;
}storage;

typedef struct{
	int frequency;
	char c;
}queue;

typedef struct tree{
	char symbol;
	int frequency;
	struct tree *lson;
	struct tree *rson;
}tree_t;

struct tree output;

void contents(storage *myStorage, int counter);
void initPQ(queue *PQ, storage *s, storage *sort, int counter);
void insert(queue *p, int rear, int length, int newF);
void heapify(queue *p, int r, int sizeP);
void toHeap(queue *p, int length);

int extract(queue *p, int length){
	int rear = length;
	if (rear == 0){
		printf("Priority Queue Underflow.\n");
		return 0;
	} else {
		output.symbol = p[0].c;
		output.frequency = p[0].frequency;
		p[0] = p[rear];
		rear = rear - 1;
		heapify(p, 0, rear);
	}
	return 0;
}

void huffman(tree_t *node, queue *p, int sizeP){
	int n = sizeP, i;
	
	for(i = 1; i < n; i++){
		//create node Z
		
		extract(p, n);
		node[i].lson = &output;
		printf("extract 1: %c,%d\t", node[i].lson->symbol, node[i].lson->frequency);
		
		extract(p, n-1);
		node[i].rson = &output;
		printf("extract 2: %c,%d\t", node[i].rson->symbol, node[i].rson->frequency);

		node[i].frequency = (node[i].lson->frequency) + (node[i].rson->frequency);

		printf("insert: %d\n", node[i].frequency - 1);
		insert (p, n, n+1, node[i].frequency - 1);
	}

}




int main(){
	
	FILE *fpointer;
	fpointer = fopen("read.txt", "r");
	int p = 0;
	int q;
	storage myStorage[52];
	char temp;
	int counter;
	bool hasmatch;


	counter = 0;													
	while(!feof(fpointer)){
		
		fscanf(fpointer,"%c", &temp);
		
		if(temp >= 65 && temp <= 90 || temp >= 97 && temp <= 122){	//check if input is char A-Z or a-z
			q = 0;													
			while(q < counter){										//traverse stored values
				if(myStorage[q].c == temp){							//if input is equal to current value, 
					myStorage[q].frqncy = myStorage[q].frqncy + 1;	//increment frequency count of current value
					hasmatch = 1;									//
					break;											//skip to the next value
				} else
					hasmatch = 0;									
				q++;
			}
			if (hasmatch == 0){										//loop ends, no match. create new slot for char
				myStorage[p].c = temp;								
				myStorage[p].frqncy = 1;
				myStorage[p].index = p;
				p++;
				counter++;
			}
		}
	}


	storage sorted[counter];
	queue PQ[counter];
	initPQ(PQ, myStorage, sorted, counter);
	toHeap(PQ, counter);
	tree_t node[(counter * 2) - 1];
	huffman(node, PQ, counter);
	

	fclose(fpointer);
}


//initialize PQ
void initPQ(queue *PQ, storage *s, storage *sort, int counter){
	int i = 0, j, holder, index;
	
	//order of insertion, smallest ascii value
	while (i < counter){
		if(i==0)
			holder = 0;
		for(j = 0; j < counter; j++){
			if(s[holder].c < s[j].c){
				holder = j;
			}
		}
		PQ[i].c = s[holder].c;
		PQ[i].frequency = s[holder].frqncy;
		s[holder].c = '0';							//set extracted value to 0
		s[holder].frqncy = 0;
		s[holder].index = -1;
		//printf("%c:%d \n", PQ[i].c, PQ[i].frequency); For checking of contents
		i++;
	}
	//printf("\n");
}

void insert(queue *p, int rear, int length, int newF){
	int i, j;

	if(rear == length){
		printf("Priority Queue overflow.\n");
		return;
	}

	rear = rear + 1;									//new rear introduces new node
	i = rear; 
	j = i / 2;
	while(i > 1 && p[j].frequency > newF){
		p[i] = p[j];
		i = j;
		j = i / 2;
	}
	p[i].frequency = newF;
	p[i].c = ' ';
}



void heapify(queue *p, int r, int sizeP){
	int i, j, k, c;
	k = p[r].frequency; 		
	c = p[r].c;
	i = r;
	j = i*2;
	while (j < sizeP){
		if (j < sizeP && p[j+1].frequency < p[j].frequency)
			j = j + 1;
		if(p[j].frequency < k){
			p[i].frequency = p[j].frequency;
			p[i].c = p[j].c;
			i = j;
			j = 2 * i;
		}
		else 
			break;
	}
	p[i].frequency = k;
	p[i].c = c;
}

//transforms tree to heap whose 
void toHeap(queue *p, int length){
	int r;
	for(r = length / 2; r > -1; r--){
		heapify(p, r, length);
	}
	
	/* Checker
	int i;
	for(i = 0; i < length; i++){
		printf("%d %c:%d \n", i, p[i].c, p[i].frequency);

	}*/
}

